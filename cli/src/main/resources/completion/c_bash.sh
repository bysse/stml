function _stml_cmd() {
  local ARGS="-h --help -c -f -no -o -s --version --kubectl"
  local SHELLS="bash"
  local FLAGS="render-null"

  if [ "$3" == "-c" ]; then
    COMPREPLY=( $(compgen -W "$SHELLS" -- $2) )
    return 0
  fi

  if [ "$3" == "-f" ]; then
    COMPREPLY=( $(compgen -W "$FLAGS" -- $2) )
    return 0
  fi

  if [ "$3" == "-o" ]; then
    local result=$(ls -1 | xargs)
    COMPREPLY=( $(compgen -W "$result" -- $2) )
    return 0
  fi

  if [ $(echo "$2" | wc -c) -gt "1" ]; then
    local result=$(ls -1 | xargs)
    COMPREPLY=( $(compgen -W "$ARGS $result" -- $2) )
  else
    local result=$(ls -1 | grep ".stml$" | xargs)
    COMPREPLY=( $(compgen -W "$ARGS $result" -- $2) )
  fi
}

complete -F _stml_cmd stml