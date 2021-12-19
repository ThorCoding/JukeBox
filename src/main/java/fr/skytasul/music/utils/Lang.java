package fr.skytasul.music.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.skytasul.music.JukeBox;

public class Lang{

	public static String NEXT_PAGE = ChatColor.AQUA + "Volgende pagina";
	public static String LATER_PAGE = ChatColor.AQUA + "Vorige pagina.";
	public static String CURRENT_PAGE = ChatColor.DARK_AQUA + "§oPagina %d van %d";
	public static String PLAYER = ChatColor.RED + "Je moet een speler zijn om dit command uit te voeren";
	public static String RELOAD_MUSIC = ChatColor.GREEN + "Muziek herladen";
	public static String INV_NAME = ChatColor.LIGHT_PURPLE + "§lGreefield Jukebox !";
	public static String TOGGLE_PLAYING = ChatColor.GOLD + "Pauze/play";
	public static String VOLUME = ChatColor.BLUE + "Muziek volume : §b";
	public static String RIGHT_CLICK = "§eRechter klik: om te verlagen met 10%";
	public static String LEFT_CLICK = "§eLinker klik: verhoog met 10%";
	public static String RANDOM_MUSIC = ChatColor.DARK_AQUA + "Random muziek.";
	public static String STOP = ChatColor.RED + "Stop de muziek.";
	public static String MUSIC_STOPPED = ChatColor.GREEN + "Muziek gestopt.";
	public static String ENABLE = "Aan";
	public static String DISABLE = "Uit";
	public static String ENABLED = "Aan";
	public static String DISABLED = "uit";
	public static String TOGGLE_SHUFFLE_MODE = "{TOGGLE} de shuffle modus";
	public static String TOGGLE_LOOP_MODE = "{TOGGLE} de loop modus";
	public static String TOGGLE_CONNEXION_MUSIC = "{TOGGLE} muziek bij joinen.";
	public static String TOGGLE_PARTICLES = "{TOGGLE} particles";
	public static String MUSIC_PLAYING = ChatColor.GREEN + "Muziek tijdens het spelen:";
	public static String INCORRECT_SYNTAX = ChatColor.RED + "Verkeerd argument.";
	public static String RELOAD_LAUNCH = ChatColor.GREEN + "Proberen te herladen.";
	public static String RELOAD_FINISH = ChatColor.GREEN + "Herlaad success.";
	public static String AVAILABLE_COMMANDS = ChatColor.GREEN + "Alle commands:";
	public static String INVALID_NUMBER = ChatColor.RED + "Onbekend nummer.";
	public static String PLAYER_MUSIC_STOPPED = ChatColor.GREEN + "Muziek gestopt voor: §b";
	public static String IN_PLAYLIST = ChatColor.BLUE + "§oIn Playlist";
	public static String PLAYLIST_ITEM = ChatColor.LIGHT_PURPLE + "Playlists";
	public static String OPTIONS_ITEM = ChatColor.AQUA + "Opties";
	public static String MENU_ITEM = ChatColor.GOLD + "Terug naar menu.";
	public static String CLEAR_PLAYLIST = ChatColor.RED + "De huidige playlist stoppen";
	public static String NEXT_ITEM = ChatColor.YELLOW + "Volgend nummer.";
	public static String CHANGE_PLAYLIST = ChatColor.GOLD + "§lVerander playlist: §r";
	public static String CHANGE_PLAYLIST_LORE = ChatColor.YELLOW + "Middle-click on a music disc\n§e to add/remove the song. Middel klik op een music disc\n§e om een liedje toe te voegen/verwijderen.";
	public static String PLAYLIST = ChatColor.DARK_PURPLE + "Playlist";
	public static String FAVORITES = ChatColor.DARK_RED + "Favorieten";
	public static String RADIO = ChatColor.DARK_AQUA + "Radio";
	public static String UNAVAILABLE_RADIO = ChatColor.RED + "Dit kan niet worden gebeurdt tijdens het luisteren van de radio.";
	public static String NONE = ChatColor.RED + "geen";

	public static void saveFile(YamlConfiguration cfg, File file) throws ReflectiveOperationException, IOException {
		for (Field f : Lang.class.getDeclaredFields()){
			if (f.getType() != String.class) continue;
			if (!cfg.contains(f.getName())) cfg.set(f.getName(), f.get(null));
		}
		cfg.save(file);
	}
	
	public static void loadFromConfig(File file, YamlConfiguration cfg) {
		List<String> inexistant = new ArrayList<>();
		for (String key : cfg.getValues(false).keySet()){
			try {
				String str = cfg.getString(key);
				str = ChatColor.translateAlternateColorCodes('&', str);
				if (JukeBox.version >= 16) str = translateHexColorCodes("(&|§)#", "", str);
				Field field = Lang.class.getDeclaredField(key);
				if (field != null) {
					field.set(key, str);
				}else inexistant.add(key);
			}catch (Exception e) {
				JukeBox.getInstance().getLogger().warning("Error bij het laden van de taal \"" + key + "\".");
				e.printStackTrace();
				continue;
			}
		}
		if (!inexistant.isEmpty())
			JukeBox.getInstance().getLogger().warning("Gevonden " + inexistant.size() + " inexistant string(s) in " + file.getName() + ": " + String.join(" ", inexistant));
	}
	
	private static final char COLOR_CHAR = '\u00A7';
	
	private static String translateHexColorCodes(String startTag, String endTag, String message) {
		final Pattern hexPattern = Pattern.compile(startTag + "([A-Fa-f0-9]{6})" + endTag);
		Matcher matcher = hexPattern.matcher(message);
		StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
		while (matcher.find()) {
			String group = matcher.group(2);
			matcher.appendReplacement(buffer, COLOR_CHAR + "x"
					+ COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
					+ COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
					+ COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5));
		}
		return matcher.appendTail(buffer).toString();
	}
	
}
