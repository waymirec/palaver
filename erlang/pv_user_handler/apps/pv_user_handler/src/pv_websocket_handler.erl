-module(pv_websocket_handler).

-export([
  init/2,
  handle/2,
  terminate/3]).

-export([
  websocket_init/1,
  websocket_handle/2,
  websocket_info/2,
  websocket_terminate/3
]).


init(Req, State) ->
  {cowboy_websocket, Req, State}.

handle(Req, State) ->
  lager:debug("Request not expected: ~p", [Req]),
  {ok, Req2} = cowboy_http_req:reply(404, [{'Content-Type', <<"text/html">>}]),
  {ok, Req2, State}.

websocket_init(State) ->
  {[{text, <<"Hello!">>}], State}.


websocket_handle(Frame = {text, Msg}, State) ->
  lager:debug("Got Data: ~p", [Msg]),
  {ok, EncodedMsg} = request_encoder:encode_text_message(Msg),
  pv_kafka_client:send(EncodedMsg),
  {[{text, << "responding to ", Msg/binary >>}], State};

websocket_handle(_Frame, State) ->
  {ok, State}.

websocket_info({log, Text}, State) ->
  lager:debug("Got Info: ~p", [Text]),
  {[{text, Text}], State};

websocket_info(_Info, State) ->
  {ok, State}.

websocket_terminate(_Reason, _Req, _State) ->
  ok.

terminate(_Reason, _Req, _State) ->
  ok.