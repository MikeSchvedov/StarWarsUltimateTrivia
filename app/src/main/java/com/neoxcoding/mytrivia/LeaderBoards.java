
package com.neoxcoding.mytrivia;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;
import com.backendless.geo.GeoPoint;

import java.util.List;
import java.util.Date;

public class LeaderBoards
{
  private Integer rank;
  private Integer score;
  private Date updated;
  private String ownerId;
  private String objectId;
  private String name;
  private String country;
  private Date created;
  public Integer getRank()
  {
    return rank;
  }

  public void setRank( Integer rank )
  {
    this.rank = rank;
  }

  public Integer getScore()
  {
    return score;
  }

  public void setScore( Integer score )
  {
    this.score = score;
  }

  public Date getUpdated()
  {
    return updated;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getName()
  {
    return name;
  }

  public void setName( String name )
  {
    this.name = name;
  }

  public String getCountry()
  {
    return country;
  }

  public void setCountry( String country )
  {
    this.country = country;
  }

  public Date getCreated()
  {
    return created;
  }

                                                    
  public LeaderBoards save()
  {
    return Backendless.Data.of( LeaderBoards.class ).save( this );
  }

  public void saveAsync( AsyncCallback<LeaderBoards> callback )
  {
    Backendless.Data.of( LeaderBoards.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( LeaderBoards.class ).remove( this );
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( LeaderBoards.class ).remove( this, callback );
  }

  public static LeaderBoards findById( String id )
  {
    return Backendless.Data.of( LeaderBoards.class ).findById( id );
  }

  public static void findByIdAsync( String id, AsyncCallback<LeaderBoards> callback )
  {
    Backendless.Data.of( LeaderBoards.class ).findById( id, callback );
  }

  public static LeaderBoards findFirst()
  {
    return Backendless.Data.of( LeaderBoards.class ).findFirst();
  }

  public static void findFirstAsync( AsyncCallback<LeaderBoards> callback )
  {
    Backendless.Data.of( LeaderBoards.class ).findFirst( callback );
  }

  public static LeaderBoards findLast()
  {
    return Backendless.Data.of( LeaderBoards.class ).findLast();
  }

  public static void findLastAsync( AsyncCallback<LeaderBoards> callback )
  {
    Backendless.Data.of( LeaderBoards.class ).findLast( callback );
  }

  public static List<LeaderBoards> find( DataQueryBuilder queryBuilder )
  {
    return Backendless.Data.of( LeaderBoards.class ).find( queryBuilder );
  }

  public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<LeaderBoards>> callback )
  {
    Backendless.Data.of( LeaderBoards.class ).find( queryBuilder, callback );
  }
}