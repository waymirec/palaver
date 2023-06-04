%%%-------------------------------------------------------------------
%% @doc message_router public API
%% @end
%%%-------------------------------------------------------------------

-module(message_router_app).

-behaviour(application).

-export([start/2, stop/1]).

start(_StartType, _StartArgs) ->
    message_router_sup:start_link().

stop(_State) ->
    ok.

%% internal functions
