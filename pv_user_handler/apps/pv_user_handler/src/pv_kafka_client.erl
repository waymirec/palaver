-module(pv_kafka_client).

-behaviour(gen_server).

%% API
-export([
  start_link/0,
  send/1
]).

%% gen_server callbacks
-export([
    init/1,
    handle_call/3,
    handle_cast/2,
    handle_info/2,
    terminate/2,
    code_change/3
]).

-define(SERVER, ?MODULE).

-record(pv_kafka_client_state, {}).

%%%===================================================================
%%% API
%%%===================================================================

start_link() ->
  gen_server:start_link({local, ?SERVER}, ?MODULE, [], []).

send(Message) when is_binary(Message) ->
  gen_server:call(?MODULE, {msg_out, Message}).

%%%===================================================================
%%% gen_server callbacks
%%%===================================================================

init([]) ->
  {ok, _} = application:ensure_all_started(brod),
  KafkaBootstrapEndpoints = [{"localhost", 29092}],
  ClientConfig = [{reconnect_cool_down_seconds, 10}],
  Result = brod:start_client(KafkaBootstrapEndpoints, kafka_client, ClientConfig),
  io:fwrite("Starting Kafka Client ~p ~n", [Result]),
  brod:start_producer(
    _Client = kafka_client,
    _Topic          = <<"TestTopic">>,
    _ProducerConfig = []),

  {ok, #pv_kafka_client_state{}}.

handle_call({msg_out, Message}, From, State = #pv_kafka_client_state{}) ->
  ok = brod:produce_sync(kafka_client, <<"TestTopic">>, random, <<"Key1">>, Message),
  {reply, ok, State};

handle_call(_Request, _From, State = #pv_kafka_client_state{}) ->
  {reply, ok, State}.

handle_cast(_Request, State = #pv_kafka_client_state{}) ->
  {noreply, State}.

handle_info(_Info, State = #pv_kafka_client_state{}) ->
  {noreply, State}.

terminate(_Reason, _State = #pv_kafka_client_state{}) ->
  ok.

code_change(_OldVsn, State = #pv_kafka_client_state{}, _Extra) ->
  {ok, State}.

%%%===================================================================
%%% Internal functions
%%%===================================================================
