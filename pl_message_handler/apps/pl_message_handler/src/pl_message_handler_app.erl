-module(pl_message_handler_app).

-behaviour(application).

-export([start/2, stop/1]).

start(_StartType, _StartArgs) ->
    pl_message_handler_sup:start_link().

stop(_State) ->
    ok.

%% internal functions
