package aroma1997.tirc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Map;
import java.util.Properties;

public class Config {
	
	private Map<?, ?> map;
	
	private Config(File file) throws IOException {
		Properties props = new Properties();
		props.load(new FileInputStream(file));
		this.map = props;
	}
	
	public int getLocalPort() {
		return Integer.parseInt(getValue("local_port"));
	}
	
	public String getTwitchIRC() {
		return getValue("twitch_host");
	}
	
	public int getTwitchPort() {
		return Integer.parseInt(getValue("twitch_port"));
	}
	
	public String getValue(String name) {
		if (map.containsKey(name)) {
			return map.get(name) + "";
		}
		return name;
	}
	
	public static Config loadConfigFile(String name) throws IOException
	{
		name += ".properties";
		File file = new File("./config/" + name);
		if (!file.exists())
		{
			// Config file doesn't exist. Create the config file from the one in
			// the classpath and save it to the disk. Then load the one from the
			// disk
			System.out.println("Did not find config file " + file.getAbsolutePath() + ".");
			System.out.println("Creating new config file.");
			Files.createDirectories(file.getParentFile().toPath());
			try (OutputStream os = new FileOutputStream(file))
			{
				try (InputStream is = Config.class.getResourceAsStream("/configs/" + name))
				{

					if (is == null)
					{
						throw new FileNotFoundException("Could not load file from classpath. /configs/" + name);
					}
					byte[] buffer = new byte[2048];
					int len;
					while ((len = is.read(buffer, 0, buffer.length)) > 0)
					{
						os.write(buffer, 0, len);
					}
				}
				os.flush();
			}
		}
		System.out.println("Loading config file from " + file.getAbsolutePath() + ".");
		return new Config(file);
	}
}
