package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.polito.tdp.genes.model.Adiacenza;
import it.polito.tdp.genes.model.CoppiaLocazioni;
import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interactions;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public List<String> getAllLocalization(){
		String sql = "SELECT DISTINCT Localization "
				+ "FROM classification";
		List<String> result = new ArrayList<String>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				result.add(res.getString("Localization"));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public Map<CoppiaLocazioni, Set<Adiacenza>> getAllAdiacenze(){
		String sql = "SELECT c1.Localization AS l1, c2.Localization AS l2, i.GeneID1, i.GeneID2, i.`Type` "
				+ "FROM classification c1, classification c2, interactions i "
				+ "WHERE c1.Localization < c2.Localization AND c1.GeneID=i.GeneID1 AND c2.GeneID=i.GeneID2 "
				+ "GROUP BY c1.Localization, c2.Localization, i.GeneID1, i.GeneID2, i.`Type` "
				+ "UNION "
				+ "SELECT c2.Localization, c1.Localization, i.GeneID2, i.GeneID1, i.`Type` "
				+ "FROM classification c1, classification c2, interactions i "
				+ "WHERE c1.Localization > c2.Localization AND c1.GeneID=i.GeneID1 AND c2.GeneID=i.GeneID2 "
				+ "GROUP BY c1.Localization, c2.Localization, i.GeneID1, i.GeneID2, i.`Type`";
		Map<CoppiaLocazioni, Set<Adiacenza>>result = new HashMap<CoppiaLocazioni, Set<Adiacenza>>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				String li = res.getString("l1");
				String l2 = res.getString("l2");
				String geneId1 = res.getString("GeneID1");
				String geneId2 = res.getString("GeneID2");
				String type = res.getString("Type");
				CoppiaLocazioni coppia = new CoppiaLocazioni(li, l2);
				if(!result.containsKey(coppia))
					result.put(coppia, new HashSet<Adiacenza>());
				Adiacenza adiacenza = new Adiacenza(li, l2, geneId1, geneId2, type);
				result.get(coppia).add(adiacenza);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}

	
}
