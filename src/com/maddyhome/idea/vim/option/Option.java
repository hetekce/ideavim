package com.maddyhome.idea.vim.option;

/*
* IdeaVim - A Vim emulator plugin for IntelliJ Idea
* Copyright (C) 2003 Rick Maddy
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Represents an VIM options that can be set with the :set command. Listeners can be set that are interested in knowing
 * when the value of the option changes.
 */
public abstract class Option
{
    /**
     * Create the option
     * @param name The name of the option
     * @param abbrev The short name
     */
    protected Option(String name, String abbrev)
    {
        this.name = name;
        this.abbrev = abbrev;
    }

    /**
     * Registers an option change listener. The listener will receive an OptionChangeEvent whenever the value of this
     * option changes.
     * @param listener The listener
     */
    public void addOptionChangeListener(OptionChangeListener listener)
    {
        listeners.add(listener);
    }

    /**
     * Removes the listener from the list.
     * @param listener The listener
     */
    public void removeOptionChangeListener(OptionChangeListener listener)
    {
        listeners.remove(listener);
    }

    /**
     * The name of the option
     * @return The option's name
     */
    public String getName()
    {
        return name;
    }

    /**
     * The short name of the option
     * @return The option's short name
     */
    public String getAbbreviation()
    {
        return abbrev;
    }

    /**
     * Checks to see if the option's current value equals the default value
     * @return True if equal to default, false if not.
     */
    public abstract boolean isDefault();

    /**
     * Sets the option to its default value.
     */
    public abstract void resetDefault();

    /**
     * Lets all listeners know that the value has changed. Subclasses are responsible for calling this when their
     * value changes.
     */
    protected void fireOptionChangeEvent()
    {
        OptionChangeEvent event = new OptionChangeEvent(this);
        for (Iterator iterator = listeners.iterator(); iterator.hasNext();)
        {
            OptionChangeListener listener = (OptionChangeListener)iterator.next();
            listener.valueChange(event);
        }
    }

    /**
     * Helper method used to sort lists of options by their name
     */
    static class NameSorter implements Comparator
    {
        public int compare(Object o1, Object o2)
        {
            return ((Option)o1).name.compareTo(((Option)o2).name);
        }
    }

    protected String name;
    protected String abbrev;
    protected ArrayList listeners = new ArrayList();
}
