package server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import server.exception.ConflictException;
import server.exception.ExistingNickNameException;
import server.exception.NameNotFoundException;

public class NickNameDataBase {

	private static NickNameDataBase instance = new NickNameDataBase();

	private Map<String, Set<String>> map;
	private Set<String> nicknames;

	private NickNameDataBase() {
		map = new HashMap<String, Set<String>>();
		nicknames = new HashSet<String>();
		init();
	}

	public static NickNameDataBase getInstance() {
		return instance;
	}

	private void init() {
		insertPerson("Luc", "manchot");
		insertPerson("Darth Vader", "son pere");
		insertPerson("Maylanie", "ECREMEUH");
		insertPerson("Thomas Clop", "Pliiinctooon");
		insertPerson("Anais", "Tortue");
		insertPerson("Delmotte", "Blondasse");
	}

	public Set<String> getNickname(String name) throws NameNotFoundException {
		Set<String> nick = map.get(name);
		if (nick != null) {
			return nick;
		} else {
			throw new NameNotFoundException();
		}
	}

	public boolean insertPerson(String name, String nickName) {
		boolean succes = false;
		if (!nicknames.contains(nickName)) {
			nicknames.add(nickName);
			Set<String> nickSet = new HashSet<String>();
			nickSet.add(nickName);
			map.put(name, nickSet);
			succes = true;
		}
		return succes;
	}

	public void addNickname(String name, String nick) throws ConflictException,
			ExistingNickNameException {
		Set<String> nicks = map.get(name);

		if (nicks == null) {
			insertPerson(name, nick);
		}
		if (nicks != null && nicks.contains(nick)) {
			throw new ConflictException();
		}
		if (nicknames.contains(nick)) {
			throw new ExistingNickNameException();
		} else {
			nicknames.add(nick);
			map.get(name).add(nick);
		}

	}

	public boolean remove(String name) {
		boolean succes = false;

		if (map.containsKey(name)) {
			nicknames.remove(map.get(name));
			map.remove(name);
			succes = true;
		}
		return succes;
	}

	@Override
	public String toString() {
		return map.toString();
	}

}
