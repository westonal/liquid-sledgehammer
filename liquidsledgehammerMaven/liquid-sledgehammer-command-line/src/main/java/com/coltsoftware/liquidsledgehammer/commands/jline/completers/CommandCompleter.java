package com.coltsoftware.liquidsledgehammer.commands.jline.completers;

import com.coltsoftware.liquidsledgehammer.Command;
import jline.console.completer.Completer;

import java.util.HashMap;
import java.util.List;

public final class CommandCompleter implements Completer {

    private final HashMap<String, Command> commands;

    public CommandCompleter(HashMap<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public int complete(String buffer, int cursor, List<CharSequence> candidates) {
        for (String key : commands.keySet()) {
            if (key.startsWith(buffer))
                candidates.add(key);
        }
        return cursor - buffer.length();
    }
}