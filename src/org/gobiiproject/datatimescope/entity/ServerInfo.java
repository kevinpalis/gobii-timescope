/* 
timescoper_id integer NOT NULL DEFAULT nextval('timescoper_timescoper_id_seq'::regclass),
    firstname text COLLATE pg_catalog."default" NOT NULL,
    lastname text COLLATE pg_catalog."default" NOT NULL,
    username text COLLATE pg_catalog."default" NOT NULL,
    password text COLLATE pg_catalog."default" NOT NULL,
    email text COLLATE pg_catalog."default",
    role integer DEFAULT 3,
    CONSTRAINT pk_timescoper PRIMARY KEY (timescoper_id),
    CONSTRAINT username_key UNIQUE (username)
*/
package org.gobiiproject.datatimescope.entity;
/**
 * User entity
 */
public class ServerInfo {
	private String host;
	private String port;
	private String dbName;
	private String userName;
	private String password;
	
	public ServerInfo() {
		//defaultValues
	      setHost("localhost");
	      setPort("5432");
	      setDbName("timescope_db2");
	      setUserName("timescoper");
	      setPassword("helloworld");
	}
	
	public ServerInfo(String host, String port, String dbName, String userName, String password) {
		this.setHost(host);
		this.setPort(port);
		this.setDbName(dbName);
		this.password = password;
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}
	

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
}
