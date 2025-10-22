# Project Config
```json
{
  "minecraft_version": "1.21.9",
  "name": "Better Ping Display",
  "author": "Becky Tidus",
  "mod_git_url": "https://github.com/vladmarica/better-ping-display-fabric",
  "enviorment": "client-side",
  "model": "haiku",
  "objective": "Update the mod to work with Minecraft 1.21.9",
  "debug": false
}
```

# Important Constraints
- ONLY work within this project directory
- DO NOT access files outside of this workspace
- All operations must be contained to the mod project folder

# Code Style
- No comments
- Max compatibility mixins
- Prefer Fabric API when possible over mixins
- If you must use a custom texture, create an empty file for the texture and note the texture name in the README.md file.

# Project Structure
- `fabric-source-code/`: Fabric API source code for Minecraft 1.21.9
- `minecraft-source-code/`: Decompiled Minecraft 1.21.9 source code
- `src/main/java/`: Source code for the mod
- `src/main/resources/`: Resource files for the mod (icon, mixins, fabric.mod.json, etc.)
- `gradle.properties`: Gradle properties for the mod
- `build.gradle`: Gradle build file for the mod

# Mixin Development Guide
Each file in `minecraft-source-code/` has mixin descriptors at the top:
- **External method calls**: Methods this class calls on other Minecraft classes
- **Internal methods**: Private/static methods within the class (common injection points)

These descriptors exclude obvious methods (simple getters/setters, constructors, no-param methods returning primitives, etc.) to reduce noise. Use these to write accurate mixin targets without hallucinating method signatures.

# Completion
When you have completed ALL tasks and the mod is fully implemented and working,
First, create a conscise and basic README.md file in the root of the project directory, simply explaining what the mod does and how to use it.
THEN, you MUST output exactly this message:
```
[FABRICATOR_COMPLETE]
```
This marker will terminate the session automatically.
