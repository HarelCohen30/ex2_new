package api;

import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import gameClient.CL_Agent;
import gameClient.CL_Pokemon;
import gameClient.Ex2_Client;
import java.util.Date;


public class Game_Server_Ex2 implements game_service {

	private dw_graph_algorithms graph;
	private List<CL_Agent> agents;
	private List<CL_Pokemon> pokemons;
	long start_game_time;
	long end_game_time=0;

	public Game_Server_Ex2()
	{
		Date now = new Date();
		start_game_time = now.getTime();
	}


	@Override
	public String getGraph() {

		String file = new String();

		//Server.Game_Server_Ex2.getServer(1);

		if(graph.save(file))
		{
			return file;
		}
		return null;

	}


	@Override
	public String getPokemons() {

		//JSONObject json_object = new JSONObject();
		JSONArray pokemon = new  JSONArray();
		Iterator<CL_Pokemon> p = pokemons.iterator();

		while (p.hasNext())
		{
			pokemon.put(p.next());
		}
		System.out.println(pokemon);
		System.out.println(pokemon.toString());
		return pokemon.toString();
	}

	@Override
	public String getAgents() {
		JSONArray agent = new  JSONArray();
		Iterator<CL_Agent> p = agents.iterator();

		while (p.hasNext())
		{
			agent.put(p.next());
		}
		System.out.println(agent);
		System.out.println(agent.toString());
		return agent.toString();
	}

	@Override
	public boolean addAgent(int start_node) {

		Iterator<CL_Pokemon> p = pokemons.iterator();
		while (p.hasNext())
		{
			CL_Pokemon pikachu= p.next();
			int src =pikachu.get_edge().getSrc();
			CL_Agent agent=new CL_Agent((directed_weighted_graph) graph,src);
		}

		return true;
	}

	@Override
	public long startGame() {
		end_game_time=0;
		Date now = new Date();
		long time = now.getTime();
		start_game_time = time;
		return time;
	}

	@Override
	public boolean isRunning() {
		if (end_game_time == 0)
		{
			return true;
		}
		return false;
	}

	@Override
	public long stopGame() {
		Date now = new Date();
		end_game_time =  now.getTime();
		return end_game_time;
	}

	@Override
	public long chooseNextEdge(int id, int next_node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long timeToEnd() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String move() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean login(long id) {
		// TODO Auto-generated method stub
		return false;
	}

}