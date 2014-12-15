# The Unnamed Game Engine



This is an unnamed game engine and can be used by anyone. It’s intended purpose is to be used for android, however, due to the flexibility of the `Framework` provided by [Kilobolt] (http://www.kilobolt.com/game-development-tutorial.html), it is simple enough to port your game to another platform by simply replacing the implementation package with one compatible with that platform.

*When referring to the engine in the document, the `Framework` and `Framework/implementation` packages will be excluded and referred to as the `Framework` specifically.*


# About the Engine

The engine uses a typical Entity-Component architecture, heavily inspired by Artemis and built to be more light-weight and simple to setup and understand.  This engine is geared towards someone who wants decent performance, ease of setup and not much overhead of learning how everything works, but something adequate to begin developing games with immediately.  

 
## The EntityManager.java Class

This is where most of the important stuff happens such as creating/destroying entities and updating entities and their components.  As per the name, it is what manages the entities.  It is fairly straight forward and includes what is necessary; creating/deleting, updating, and rendering.  To create an entity, it is encouraged to use the EntityBuilder inner class for simplicity, an example can be found in the EntityFactory class.  

## Screens (And Basescreen)

The `screen` is what is currently being rendered, and the `screen` child class that is being used should keep track of switching `screen`s.  To make things simple, everything is rendered to the `screen` directly. `Screen`s don’t keep track of entities that are on them, and if you don’t remove entities on switching screens they will persist (as the `EntityManager` class acts as a singleton and is only created once, not on each screen).  

# Improvements

There is a few things I can see that would be useful in the near future:

* An event system, specifically for switching screens and notifying entities to be destroyed/created
* The combination of World and GameEngine into the same class, and abstract the Android specific code in Game to be a part of something in Framework (maybe move it to AndroidGame)
* Google play integration for leaderboards and achievements
* More efficient methods of scaling images dynamically (for rendering, not changing image file dimensions)
* Multiple map supports (currently, only tiled-maps are supported)
* OpenGL instead of canvas for more flexibility
* A tool for developing UI for menus/end screens
* more that I am currently forgetting


### About the demo

When running the game right after cloning, you will see a moon fade in, then a button to embark.  The moon is the loading screen, and the embark is a simple menu.  The tiles is the actual game, and the camera supports panning and zooming.  

Happy coding :)

