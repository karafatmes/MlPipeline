package graph.analyzer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import entities.Column;
import services.GraphCreator;

class ServiceTest {

	
	
	@Test
	public void testRemoveCommaMethod() {
		GraphCreator creator = new GraphCreator();
		String weight = "a,b,v,";
		assertEquals(creator.removeLastComma(weight),"a,b,v");
		
		String otherWeight = "feature1, feature2, feature3 ,";
		assertEquals(creator.removeLastComma(otherWeight),"feature1, feature2, feature3 ");
	}
	
	
	@Test
	public void testisContainedInNodeAsInputMethod() {
		GraphCreator creator = new GraphCreator();
		List<Column> inputCols = new ArrayList<Column>();
		Column column1= new Column("column1");
		inputCols.add(column1);
		Column column2= new Column("column2");
		inputCols.add(column2);
		Column column3= new Column("column3");
		inputCols.add(column3);
		String outputCol = "column1";
		assertEquals(creator.isContainedInNodeAsInput(inputCols, outputCol),true);
		
		
		List<Column> otherInputCols = new ArrayList<Column>();
		Column otherColumn1= new Column("otherColumn1");
		otherInputCols.add(otherColumn1);
		Column otherColumn2= new Column("otherColumn2");
		otherInputCols.add(otherColumn2);
		Column otherColumn3= new Column("otherColumn3");
		otherInputCols.add(otherColumn3);
		String otherOutputCol = "column2";
		assertEquals(creator.isContainedInNodeAsInput(otherInputCols, otherOutputCol),false);
		
		
		
	}
	
	
	

}
