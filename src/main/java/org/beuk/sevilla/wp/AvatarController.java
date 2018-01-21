package org.beuk.sevilla.wp;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.Date;

import org.apache.commons.configuration.*;
import org.slf4j.*;

public class AvatarController {

	final static String avatarString = "select a.ID, b.meta_value, a.user_login, c.meta_value from wp_users as a, wp_usermeta as b, wp_postmeta as c where a.ID = b.user_id and b.meta_key = 'wp_user_avatar' and b.meta_value = c.post_id and c.meta_key = '_wp_attached_file'";

	public static void copyFile(File from, File to) throws IOException {

		final CopyOption[] options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES };
		Files.copy(from.toPath(), to.toPath(), options);
	}

	private final Logger log = LoggerFactory.getLogger(AvatarController.class);

	String dbHost;
	String dbName;
	String dbUser;
	String dbPwd;
	String connectionString;
	String wpUpload;
	String wpAvatars;
	String avatarOutputDir;

	public AvatarController(PropertiesConfiguration propertyController) {

		dbHost = propertyController.getString("db.host", "localhost");
		dbName = propertyController.getString("db.name", "wordpress");
		dbUser = propertyController.getString("db.user", "wordpress");
		dbPwd = propertyController.getString("db.password", "notset");
		wpUpload = propertyController.getString("wp.upload", "/var/www/wp-content/uploads/");
		wpAvatars = propertyController.getString("wp.avatars", "/var/www/wp-content/avatars/");
		avatarOutputDir = propertyController.getString("web.avatar.outputpath", "/var/www/wp-content/avatars/");
		connectionString = "jdbc:mysql://" + dbHost + "/" + dbName + "?user=" + dbUser + "&password=" + dbPwd;
		log.info("connection string: " + connectionString);
	}

	public void checkForNewAvatars() {

		System.out.println("checking for avatars");
		try (Connection conn = DriverManager.getConnection(connectionString); Statement stmt = conn.createStatement()) {
			final ResultSet rs = stmt.executeQuery(avatarString);
			while (rs.next()) {
				final String username = rs.getString(3);
				final String avatarBase = rs.getString(4).substring(0);
				final String extention = avatarBase.substring(avatarBase.lastIndexOf('.'));
				String avatar = wpUpload + avatarBase.substring(0, avatarBase.lastIndexOf('.')) + "-150x150" + extention;
				final String avatarDest = wpAvatars + username.toLowerCase();
				if (!copyAvatar(avatarBase, username, avatar, avatarDest)) {
					avatar = wpUpload + avatarBase;
					if (!copyAvatar(avatarBase, username, avatar, avatarDest)) {
						System.err.println("unable to copy base image: " + avatarBase);
					}
				}
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}

	}

	boolean copyAvatar(String avatarBase, String username, String avatar, String avatarDest) {

		final File f = new File(avatar);
		if (f.exists()) {
			System.out.println(username + " -> " + avatar + " s -> " + f.length() + " d -> " + new Date(f.lastModified()));

			final File fd = new File(avatarDest);
			if (!fd.exists() || f.lastModified() > fd.lastModified()) {
				try {
					System.out.println("copy to avatar: " + avatarDest);
					AvatarController.copyFile(f, fd);
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
			return true;
		} else {
			System.err.println(avatarBase + " not found: " + username + " -> " + avatar);
		}
		return false;

	}

}
