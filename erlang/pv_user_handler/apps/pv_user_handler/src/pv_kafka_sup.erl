-module(pv_kafka_sup).

-behaviour(supervisor).

%% API
-export([start_link/0]).

%% Supervisor callbacks
-export([init/1]).

-define(SERVER, ?MODULE).


start_link() ->
  supervisor:start_link({local, ?SERVER}, ?MODULE, []).


init([]) ->
  MaxRestarts = 1000,
  MaxSecondsBetweenRestarts = 3600,
  SupFlags = #{strategy => one_for_all,
    intensity => MaxRestarts,
    period => MaxSecondsBetweenRestarts},

  KafkaClient = #{
    id => pv_kafka_client,
    start => {pv_kafka_client, start_link, []},
    restart => permanent,
    shutdown => 2000,
    type => worker,
    modules => [pv_kafka_client]
  },

  KafkaConsumer = #{id => pv_kafka_consumer,
    start => {pv_kafka_consumer, start_link, []},
    restart => permanent,
    shutdown => 2000,
    type => worker,
    modules => [pv_kafka_consumer]},

  {ok, {SupFlags, [KafkaClient,KafkaConsumer]}}.
