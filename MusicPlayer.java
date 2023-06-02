import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MusicPlayer extends JFrame implements ActionListener {
    private JButton selectSongButton;
    private JButton playButton;
    private JButton stopButton;
    private JButton previousButton;
    private JButton nextButton;
    private JButton shuffleButton;
    private JLabel currentlyPlayingLabel;
    private JLabel headingLabel;
    private JList<String> songList;
    private DefaultListModel<String> listModel;
    private Player player;
    private List<String> musicList;
    private int currentTrackIndex;

    public MusicPlayer() {
        setTitle("Music Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        headingLabel = new JLabel("Music Player");
        headingLabel.setForeground(new Color(0, 255, 225)); // Turquoise text color
        headingLabel.setFont(new Font("Arial", Font.BOLD, 60));

        shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener(this);
        previousButton = new JButton("Previous");
        previousButton.addActionListener(this);
        playButton = new JButton("Play");
        playButton.addActionListener(this);
        stopButton = new JButton("Pause");
        stopButton.addActionListener(this);
        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        selectSongButton = new JButton("Select Song");
        selectSongButton.addActionListener(this);
        currentlyPlayingLabel = new JLabel("Currently Playing: ");
        currentlyPlayingLabel.setForeground(new Color(0, 255, 225)); // Turquoise text color

        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.setBackground(Color.BLACK);
        controlPanel.add(shuffleButton);
        controlPanel.add(previousButton);
        controlPanel.add(playButton);
        controlPanel.add(stopButton);
        controlPanel.add(nextButton);
        controlPanel.add(selectSongButton);

        listModel = new DefaultListModel<>();
        songList = new JList<>(listModel);
        songList.setBackground(Color.BLACK);
        songList.setForeground(new Color(0, 255, 225)); // Turquoise text color
        JScrollPane scrollPane = new JScrollPane(songList);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBackground(Color.BLACK);
        topPanel.add(headingLabel);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.add(currentlyPlayingLabel);
        add(topPanel, BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.PAGE_END);
        add(controlPanel, BorderLayout.PAGE_END);

        musicList = new ArrayList<>();
        currentTrackIndex = 0;
    }

    public void playMusic(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            player = new Player(fis);
            Thread t = new Thread(() -> {
                try {
                    player.play();
                    if (!player.isComplete()) {
                        playNextTrack();
                    }
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            });
            t.start();
            currentlyPlayingLabel.setText("Currently Playing: " + filename);
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            stopMusic();
        }
    }

    public void stopMusic() {
        if (player != null) {
            player.close();
            player = null;
        }
    }

    public void playNextTrack() {
        currentTrackIndex++;
        if (currentTrackIndex >= musicList.size()) {
            currentTrackIndex = 0;
        }
        stopMusic();
        playMusic(musicList.get(currentTrackIndex));
    }

    public void playPreviousTrack() {
        currentTrackIndex--;
        if (currentTrackIndex < 0) {
            currentTrackIndex = musicList.size() - 1;
        }
        stopMusic();
        playMusic(musicList.get(currentTrackIndex));
    }

    public void playRandomTrack() {
        Random random = new Random();
        int randomIndex = random.nextInt(musicList.size());
        currentTrackIndex = randomIndex;
        stopMusic();
        playMusic(musicList.get(currentTrackIndex));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectSongButton) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String filename = fileChooser.getSelectedFile().getAbsolutePath();
                musicList.add(filename);
                listModel.addElement(filename);
            }
        } else if (e.getSource() == playButton) {
            int selectedIndex = songList.getSelectedIndex();
            if (selectedIndex != -1) {
                String selectedSong = listModel.getElementAt(selectedIndex);
                stopMusic();
                playMusic(selectedSong);
            }
        } else if (e.getSource() == stopButton) {
            stopMusic();
        } else if (e.getSource() == previousButton) {
            playPreviousTrack();
        } else if (e.getSource() == nextButton) {
            playNextTrack();
        } else if (e.getSource() == shuffleButton) {
            playRandomTrack();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MusicPlayer musicPlayer = new MusicPlayer();
            musicPlayer.setSize(400, 300);
            musicPlayer.setVisible(true);
        });
    }
}