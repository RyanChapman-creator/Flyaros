# Flyaros (Ο Φλύαρος)

A simple chatbot written in Java

## Responses

Responding with goodbye to the chatbot will cause the program to exit the main loop and begin attempting to save the chatbot state.

## Data Files

Flyaros data files can be specified as the first program argument. If omitted then the program will prompt the user if a data file should be loaded and where it is located.

When the user exits the main loop, the program will ask if the user wants to save the state of the chatbot. Responding with yes will trigger the program to first check if a save file was provided during loading. It it was provided, then it will ask the user if it should use that file. If the user responds with no or the file was not specified, the program will then ask for the file location. It will then save the chatbot state. The program will always use the most recent version of the data file format.

If the user had instead responded with no, the program will discard the current state of the chatbot and terminate, effectively wiping the memory of the interaction.

Data files can end in either .fly or .dat. The former indicates files specifically for Flyaros, and the latter indicates files meant to be used with other projects.

The data files must start with "flyaros.datafile" followed by a space. The rest of the line must have the version of the data file prefixed with 'v'. 1.0.0 - 1.2.0 are the only supported versions at this time.

The next line must start with "bot.name=" and end with the name of the chatbot. It is recommended that names are lowercased, but this will also be done automatically.

In version 1.1.0, the next line must have the name of the user. Again the line must with the attribute name "user.name" followed by '=' followed by the name of the name in lowercase.

In both v1.0.0 and v1.1.0, any lines following the required attributes are ignored.

Starting from version 1.2.0 onward, the attributes may appear in any order.

Example v1.0.0:
```
flyaros.datafile v1.0.0
bot.name=flyaros
```

Example v1.1.0:
```
flyaros.datafile v1.1.0
bot.name=flyaros
user.name=user
```

Example v1.2.0:
```
flyaros.datafile v1.1.0
user.name=user
bot.name=flyaros
```