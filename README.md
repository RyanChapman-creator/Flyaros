# Flyaros (Ο Φλύαρος)

A simple chatbot written in Java

## Responses

Responding with goodbye to the chatbot will cause the program to exit the main loop and begin attempting to save the chatbot state.

## Data Files

Flyaros data files can be specified as the first program argument. If omitted then the program will prompt the user if a data file should be loaded and where it is located.

When the user exits the main loop, the program will ask if the user wants to save the state of the chatbot. Responding with yes will prompt the program to ask for the file location. Responding with no will discard the current state of the chatbot - effectively wiping the memory of the interaction.

Data files can end in either .fly or .dat. The former indicates files specifically for Flyaros, and the latter indicates files meant to be used with other projects.

The data files must start with "flyaros.datafile" followed by a space. The rest of the line must have the version of the data file prefixed with 'v'. 1.0.0 is the only supported version at this time.

The next line must start with "bot.name=" and end with the name of the chatbot. It is recommended that names are lowercased, but this will also be done automatically. Any following lines are ignored.

Example:
```
flyaros.datafile v1.0.0
bot.name=flyaros
```