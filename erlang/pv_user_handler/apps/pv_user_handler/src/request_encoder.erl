-module(request_encoder).

%% API
-export([
  encode_text_message/1
]).

-include("opcodes.hrl").

encode_text_message(Body) when is_binary(Body) ->
  VsnBin = pack(1),
  OpcodeBin = pack(?OPCODE_SEND_MESSAGE),
  TypeBin = pack(<<"text">>),
  BodyBin = pack(Body),
  Result = <<VsnBin/binary, OpcodeBin/binary, TypeBin/binary, BodyBin/binary>>,
  {ok, Result}.


pack(Value) -> msgpack:pack(Value, [{spec, new}]).