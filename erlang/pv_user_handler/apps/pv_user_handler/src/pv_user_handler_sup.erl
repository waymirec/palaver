-module(pv_user_handler_sup).

-behaviour(supervisor).

-export([start_link/0]).

-export([init/1]).

-define(SERVER, ?MODULE).

start_link() ->
  supervisor:start_link({local, ?SERVER}, ?MODULE, []).

init([]) ->
  SupFlags = #{strategy => one_for_one,
                 intensity => 0,
                 period => 1},
  ChildSpecs = [
    #{id => pv_kafka_sup,
      start => {pv_kafka_sup, start_link, []},
      restart => permanent,
      shutdown => 2000,
      type => supervisor,
      modules => [pv_kafka_sup]
    }
  ],
  {ok, {SupFlags, ChildSpecs}}.

