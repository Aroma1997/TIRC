package aroma1997.tirc;

import java.lang.reflect.InvocationTargetException;

public enum Side {
	
	USER(UserToTwitchConnection.class), TWITCH(SingleConnection.class);
	
	private Class<? extends SingleConnection> clazz;

	private Side(Class<? extends SingleConnection> clazz) {
		this.clazz = clazz;
	}
	
	public Side getOpposite() {
		return Side.values()[~ordinal() & 1];
	}
	
	public SingleConnection getConnection(ClientConnection conn) {
		try {
			return clazz.getDeclaredConstructor(ClientConnection.class).newInstance(conn);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
