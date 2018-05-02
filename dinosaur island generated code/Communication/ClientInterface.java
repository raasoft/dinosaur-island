package Dinosaur Island.Communication;

import java.io.*;
import java.util.*;

public interface ClientInterface {

	public void createPlayer(String theUsername, String thePassword);

	public void loginPlayer(String theUsername, String thePassword);

}
