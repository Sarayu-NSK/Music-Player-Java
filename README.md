# Music-Player-Java

The provided code is an implementation of a music player application using JavaSwing.
It offers a graphical user interface (GUI) that allows users to play, pause, stop, and
navigate through a collection of music files. The application supports features like
playlist creation, shuffle playback, and song selection.
The MusicPlayer class extends the JFrame class, serving as the main window of the
application. It contains various components such as buttons, labels, and a list to display
the music tracks. The GUI elements are organized using different layout managers to
ensure an aesthetically pleasing and functional design.

Upon launching the application, the GUI is created and initialized. The user interface
includes buttons for shuffle, previous track, play, stop, and next track, as well as a select
song button for adding music files to the playlist. The currently playing label displays
the name of the currently playing song.

The music tracks are stored in a list, and their filenames are displayed in a JList
component using a DefaultListModel. Users can select a song from the list and click the
play button to start playback. The selected song is played using the JavaZoom MP3
library, specifically the Player class.

The code includes methods to handle music playback operations. The playMusic()
method takes a filename as input and initiates playback of the corresponding music file.
It creates a new Player object and plays the file in a separate thread. If the playback is
complete, it proceeds to play the next track in the playlist. The stopMusic() method
stops the current playback by closing the Player object.

Additionally, there are methods to handle playing the next and previous tracks in the
playlist, as well as a method to play a random track. These methods update the current
track index, stop the current playback, and initiate playback of the corresponding track.
The actionPerformed() method implements the ActionListener interface to handle
button click events. It checks the source of the event and performs the corresponding
actions, such as selecting a song, playing, stopping, skipping to the previous or next
track, or shuffling the playlist.

The main() method sets up the MusicPlayer object, sets the size of the application
window, and makes it visible using the SwingUtilities.invokeLater() method.
Overall, this JavaSwing-based music player application provides a simple yet functional
interface for users to manage and enjoy their music collection
