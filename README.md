# Show My Skin Parts

A simple mod for Minecraft 1.20.4/6 & 1.21.x (Other versions are not tested).  
The configure screen requires `Cloth Config` and `Mod Menu` to open (or you can edit the configuration file manually).

### Why use it and What does it do?

For some reason, some minecraft servers (especially 1.19.3) won't send you your skin parts if you enter another
dimension,
and it makes your skin looks **ugly**.

With this mod, your skin layers will always be shown (Following the client's skin options).  
And it will automatically resend your client's options in order to enable your skin layers to be seen to others (Configurable).  
If other players still cannot see your skin layers, you can simply press `N` to refresh your skin parts manually.

### Notice

This mod doesn't seem work well with 3d-Skin-Layers, so please disable it first and try again before reporting bugs if
it's installed.

### Compiling

To compile on *nix operating systems, run:

```shell
chmod +x gradlew
./gradlew build
```

For Windows, run:

```batch
gradlew build
```

Then, the mod jar file will be located at `fabricWrapper/build/libs/ShowMySkinParts-{VERSION}-all.jar`.