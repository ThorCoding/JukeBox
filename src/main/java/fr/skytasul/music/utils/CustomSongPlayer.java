package fr.skytasul.music.utils;

import org.bukkit.entity.Player;

import com.xxmicloxx.NoteBlockAPI.model.Layer;
import com.xxmicloxx.NoteBlockAPI.model.Playlist;
import com.xxmicloxx.NoteBlockAPI.model.SoundCategory;
import com.xxmicloxx.NoteBlockAPI.model.playmode.StereoMode;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;

import fr.skytasul.music.JukeBox;

public class CustomSongPlayer extends RadioSongPlayer{

	private boolean particlesEnabled = false;
	
	public boolean adminPlayed;
	
	public CustomSongPlayer(Playlist playlist){
		super(playlist, SoundCategory.RECORDS);
		try {
			super.setChannelMode(new StereoMode());
		}catch (Exception ex) {
			JukeBox.getInstance().getLogger().warning("Update de versie van noteblock api aub.");
		}
		if (JukeBox.useExtendedOctaveRange) super.setEnable10Octave(true);
	}
	
	public void setParticlesEnabled(boolean particles){
		if (JukeBox.particles) this.particlesEnabled = particles;
	}

	@Override
	public void playTick(Player player, int tick){
		super.playTick(player, tick);
		if (!particlesEnabled) return;
		for (Layer layer : this.song.getLayerHashMap().values()) {
			if (layer.getNote(tick) != null) {
				Particles.sendParticles(player);
				break;
			}
		}
	}
	
}
