-module(pv_kafka_consumer).

-include_lib("brod/include/brod.hrl").

-behaviour(gen_server).

%% API
-export([start_link/0]).

-export([
  init/2,
  handle_message/4
]). %% callback api

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

-record(pv_kafka_consumer_state, {}).

init(_GroupId, _Arg) -> {ok, []}.

%% brod_group_subscriber behaviour callback
handle_message(_Topic, Partition, Message, State) ->
  io:fwrite("Kafka message ~p ~n", [Message]),
  {ok, ack, State}.


%% gen_srv callbacks
start_link() ->
  gen_server:start_link({local, ?SERVER}, ?MODULE, [], []).

init([]) ->
  Topic  = <<"TestTopic">>,
  %% commit offsets to kafka every 5 seconds
  GroupConfig = [{offset_commit_policy, commit_to_kafka_v2},
    {offset_commit_interval_seconds, 5}
  ],
  GroupId = <<"test_group_id">>,
  ConsumerConfig = [{begin_offset, earliest}],
  brod:start_link_group_subscriber(kafka_client, GroupId, [Topic],
    GroupConfig, ConsumerConfig,
    _CallbackModule  = ?MODULE,
    _CallbackInitArg = []),

  {ok, #pv_kafka_consumer_state{}}.

handle_call(_Request, _From, State = #pv_kafka_consumer_state{}) ->
  {reply, ok, State}.

handle_cast(_Request, State = #pv_kafka_consumer_state{}) ->
  {noreply, State}.

handle_info(_Info, State = #pv_kafka_consumer_state{}) ->
  {noreply, State}.

terminate(_Reason, _State = #pv_kafka_consumer_state{}) ->
  ok.

code_change(_OldVsn, State = #pv_kafka_consumer_state{}, _Extra) ->
  {ok, State}.

