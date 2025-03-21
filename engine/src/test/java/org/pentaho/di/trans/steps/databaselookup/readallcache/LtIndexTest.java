/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.di.trans.steps.databaselookup.readallcache;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.BitSet;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * @author Andrey Khayrutdinov
 */
@RunWith( Parameterized.class )
public class LtIndexTest extends IndexTestBase<LtIndex> {

  @Parameterized.Parameters
  public static List<Object[]> createSampleData() {
    return IndexTestBase.createSampleData();
  }

  public LtIndexTest( Long[][] rows ) {
    super( LtIndex.class, rows );
  }

  @Override
  void doAssertMatches( BitSet candidates, long lookupValue, long actualValue ) {
    if ( !( actualValue < lookupValue ) ) {
      fail( String.format( "All found values are expected to be less than [%d], but got [%d] among %s",
        lookupValue, actualValue, candidates ) );
    }
  }


  @Override
  public void lookupFor_MinusOne() {
    testFindsNothing( -1 );
  }

  @Override
  public void lookupFor_Zero() {
    testFindsNothing( 0 );
  }

  @Override
  public void lookupFor_One() {
    // should be [0]
    testFindsCorrectly( 1, 1 );
  }

  @Override
  public void lookupFor_Two() {
    // should be [0, 1]
    testFindsCorrectly( 2, 2 );
  }

  @Override
  public void lookupFor_Three() {
    // should be [0, 1, 2, 2]
    testFindsCorrectly( 3, 4 );
  }

  @Override
  public void lookupFor_Hundred() {
    // should be all
    testFindsCorrectly( 100, 5 );
  }
}
