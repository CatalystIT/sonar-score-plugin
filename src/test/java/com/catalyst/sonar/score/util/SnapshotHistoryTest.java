package com.catalyst.sonar.score.util;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class SnapshotHistoryTest {
	
	
	private Date buildDate = new Date(1371193200000l);	
	private BigDecimal measureValue = new BigDecimal(500.00);
	private SnapshotHistory sh = new SnapshotHistory (measureValue, buildDate);
	
	@Before
	public void setUp(){
	sh.setBuildDate(buildDate)	;
	sh.setMeasureValue(measureValue);
	}
	
	
/**
 * Testing that the build date returned when calling the getBuildDate method
 */
	@Test
	public void testGetBuildDate(){
		assertSame(buildDate, sh.getBuildDate());
	}

/**
 * Testing that the measure value is returned when calling the getMeasureValue method
 */
	@Test
	public void testGetMeasureValue(){
		assertSame(measureValue, sh.getMeasureValue());
	}
}
