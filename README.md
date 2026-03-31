# Individual Assignment 4 – Gyroscope-Controlled Ball Game


## Features

- Uses the **gyroscope sensor** (`Sensor.TYPE_GYROSCOPE`) to detect device tilt and update the ball’s position.
- Renders the ball and maze using a **Compose `Canvas`**, with a green background and black rectangular walls.
- Performs basic **collision detection** between the ball and walls so the ball cannot pass through obstacles or the outer frame.

## How it works

- `MainActivity` implements `SensorEventListener` and registers the gyroscope in `onResume`, unregistering it in `onPause`. 
- Sensor values (`tiltX`, `tiltY`) are stored in `mutableStateOf` fields and passed into the `GameScreen` composable, causing recomposition when the device tilts.
- `GameScreen`:
  - Keeps the ball’s center position (`ballX`, `ballY`) in `rememberSaveable` state so it survives recomposition and configuration changes (e.g., rotation). 
  - Draws the maze walls as a list of `Rect` objects and checks whether the ball’s next position would intersect any wall, cancelling movement on that axis if there is a collision.  
  - Draws the green background, walls, and white ball each frame on the `Canvas`.

## Demo

[demo.webm](https://github.com/user-attachments/assets/005d2a63-ffbf-413c-b4c5-e9598a65908d)

## Use of AI

AI assistance (Perplexity, powered by GPT-5.1) was used to:

- Sketch out the basic Canvas drawing logic and the structure for rectangular maze walls.
- Provide example patterns for gyroscope setup and sensor event handling in Kotlin.

One suggested change from the AI was **rejected**: it initially used `remember` for the ball position state; this was changed to `rememberSaveable` because moving the ball can trigger recompositions, and `rememberSaveable` preserves the ball’s position across those events.
