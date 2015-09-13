package com.coltsoftware.liquidsledgehammer.commands.jline.completers;

import com.coltsoftware.liquidsledgehammer.Command;
import com.coltsoftware.liquidsledgehammer.State;
import jline.console.completer.Completer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class CommandCompleter implements Completer {

    private State state;
    private final HashMap<String, Command> commands;

    public CommandCompleter(State state, HashMap<String, Command> commands) {
        this.state = state;
        this.commands = commands;
    }

    @Override
    public int complete(String buffer, int cursor, List<CharSequence> candidates) {
        List<CharSequence> temp = new ArrayList<>();
        for (String key : commands.keySet()) {
            if (key.startsWith(buffer))
                temp.add(key);
            String prefix = key + " ";
            if (buffer.startsWith(prefix)) {
                Completer commandCompleter = commands.get(key).getCompleter(state);
                if (commandCompleter != null) {
                    String shortBuffer = buffer.substring(prefix.length());
                    return commandCompleter.complete(shortBuffer, cursor - prefix.length(), candidates) + prefix.length();
                }
            }
        }
        candidates.addAll(temp);
        return cursor - buffer.length();
    }
}