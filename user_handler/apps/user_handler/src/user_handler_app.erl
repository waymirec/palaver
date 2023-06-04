%%%-------------------------------------------------------------------
%% @doc user_handler public API
%% @end
%%%-------------------------------------------------------------------

-module(user_handler_app).

-behaviour(application).

-export([start/2, stop/1]).

start(_StartType, _StartArgs) ->
    lager:start(),

    Dispatch = cowboy_router:compile([
        {'_', [
            {"/", cowboy_static, {priv_file, erws, "index.html"}},
            {"/websocket", websocket_handler, []}
        ]}
    ]),

    {ok, _} = cowboy:start_clear(my_http_listener,
        [{port, 8080}],
        #{env => #{dispatch => Dispatch}}
    ),

    user_handler_sup:start_link().

stop(_State) ->
    ok.

%% internal functions
