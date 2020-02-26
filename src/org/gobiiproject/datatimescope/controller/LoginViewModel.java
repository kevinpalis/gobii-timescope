package org.gobiiproject.datatimescope.controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.gobiiproject.datatimescope.db.generated.tables.records.TimescoperRecord;
import org.gobiiproject.datatimescope.entity.ServerInfo;
import org.gobiiproject.datatimescope.exceptions.TimescopeException;
import org.gobiiproject.datatimescope.services.AuthenticationService;
import org.gobiiproject.datatimescope.services.ServiceFactory;
import org.gobiiproject.datatimescope.services.UserCredential;
import org.gobiiproject.datatimescope.services.ViewModelService;
import org.gobiiproject.datatimescope.utils.WebappUtil;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class LoginViewModel {
	final static Logger log = Logger.getLogger(LoginViewModel.class.getName());
	//UI component

	@Wire
	Window loginWin;
	
	
	boolean isCreateNew = false;

	private ServerInfo serverInfo;

	private String pageCaption;

	private TimescoperRecord userAccount;

	private ListModelList<String> roleList;

	ViewModelService viewModelService;


	private void prefillServerInfoFromCookies() {
		log.debug("Prefilling server info from cookie values");
		this.serverInfo = new ServerInfo();
		//Load cookies --
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		Cookie[] cookies = request.getCookies();

		for (Cookie cookie: cookies) {
			String value = cookie.getValue();
			log.debug(String.format("Cookie: %s %s", cookie.getName(), cookie.getValue()));
			if (cookie.getName().equals("DB_HOST")){
				this.serverInfo.setHost(value);
			}
			else if (cookie.getName().equals("DB_PORT")) {
				this.serverInfo.setPort(value);
			}
			else if (cookie.getName().equals("DB_NAME")) {
				this.serverInfo.setDbName(value);
			}
			else if (cookie.getName().equals("TS_NAME")) {
				this.serverInfo.setUserName(value);
			}
		}

	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		this.prefillServerInfoFromCookies();
		Selectors.wireComponents(view, this, false);
	}
   
	@Init
	public void init() {
		AuthenticationService authService = ServiceFactory.getAuthenticationService();//new AuthenticationServiceImpl();

		UserCredential cre = authService.getUserCredential();
		if(cre==null){

			userAccount = new TimescoperRecord();
			//serverInfo = new ServerInfo();

			Map<String, Object> args = new HashMap<String, Object>();
			args.put("isLoggedIn", false);
			viewModelService = ServiceFactory.getViewModelService();
		} else{
			Executions.sendRedirect("/index.zul");
			return;
		}
	}


	@Command("needhelp")
	public void needhelp() {
		Window window = (Window)Executions.createComponents(
				"needHelp.zul", null, null);
		window.doModal();
	}

	private void disableLoginComponents() {
		log.info("Disabling loginWindow");
		setComponentEnabled(loginWin, true);
	}
	
	private void enableLoginComponents() {
		log.info("Enabling loginWindow");
		setComponentEnabled(loginWin, false);
	}

	private void setComponentEnabled(AbstractComponent pComponent, boolean disabled) {
		if (pComponent == null) return;
		for( Object o : pComponent.getChildren() ) {

			AbstractComponent ac = ( AbstractComponent ) o;
			try {
				Method m = ac.getClass().getMethod( "setDisabled", Boolean.TYPE );
				m.invoke( ac, disabled);
			} catch( Exception e ) {
			}

			List children = ac.getChildren();
			if( children != null ) {
				setComponentEnabled(ac, disabled);
			}
		}

	}
	
	private void doLogin() throws InterruptedException{
		try {
			//.debug("Showing busy state...");
			

			//File configFile = new File( System.getProperty("user.dir")+"/config.properties");
			try {
				ServletContext context = Executions.getCurrent().getDesktop().getWebApp().getServletContext();

				String dbusername = (String) context.getAttribute("DB_USERNAME"); //TODO: externalize constant
				String dbpassword = (String) context.getAttribute("DB_PASSWORD");

				if(dbusername != null && dbpassword != null){	
					log.info("Setting DB username/password to session of user");
					serverInfo.setUserName(dbusername);
					serverInfo.setPassword(dbpassword);
					Map<String, Object> args = new HashMap<String, Object>();
					args.put("isLoggedIn", false);
				} else {
					throw new NullPointerException();
				}
				//		} catch (FileNotFoundException ex) {
				//			// file does not exist
				//			log.error("cannot find config properties file: "+ configFile.getAbsolutePath());
				//		} catch (IOException ex) {
				//			// I/O error
				//			log.error("i/o exception"+ ex.getMessage());
			} catch (NullPointerException ex) {
				// file does not exist
				log.error("Null values were retrieved from config properties file");
			}

			viewModelService.connectToDB(serverInfo.getUserName(), serverInfo.getPassword(), serverInfo);


			AuthenticationService authService = ServiceFactory.getAuthenticationService();

			if (authService.login(userAccount.getUsername(), userAccount.getPassword())){
				log.info(String.format("Login successful for user %s", userAccount.getUsername()));

				serverInfo.setUserName("dummyusername");
				serverInfo.setPassword("dummypassword");

				log.debug(String.format("Database: %s:%s/%s",  serverInfo.getHost(), serverInfo.getPort(), serverInfo.getDbName()));

				HttpServletResponse response = (HttpServletResponse)Executions.getCurrent().getNativeResponse();
				response.addCookie(
						new Cookie("DB_HOST", serverInfo.getHost())
						);
				response.addCookie(
						new Cookie("DB_PORT", serverInfo.getPort())
						);
				response.addCookie(
						new Cookie("DB_NAME", serverInfo.getDbName())

						);
				response.addCookie(
						new Cookie("TS_NAME", serverInfo.getUserName())			
						);



				//				try {
				//					Properties properties = new Properties();
				//					properties.setProperty("db.host", serverInfo.getHost());
				//					properties.setProperty("db.port", serverInfo.getPort());
				//					properties.setProperty("db.name", serverInfo.getDbName());
				//
				//					ClassLoader classLoader = getClass().getClassLoader();
				//					File file = new File(classLoader.getResource("db.properties").getFile());
				//
				//					FileOutputStream fileOut = new FileOutputStream(file);
				//					properties.store(fileOut, null);
				//					fileOut.close();
				//				} catch (FileNotFoundException e) {
				//					e.printStackTrace();
				//				} catch (IOException e) {
				//					e.printStackTrace();
				//				}

				//Messagebox.show("Login successful!", "", Messagebox.OK, Messagebox.INFORMATION);
				
				//Executions.sendRedirect("/index.zul");
				Clients.clearBusy();
				
				Events.echoEvent(Events.ON_CLIENT_INFO, loginWin, "redirect");

			}


		} catch (TimescopeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Clients.clearBusy();
			this.enableLoginComponents();
			WebappUtil.showErrorDialog(e1);

		}
	}
	
	@Command("login")
	public void openDatabaseInfoDialog() {
		this.disableLoginComponents();
		Clients.showBusy("Logging in...");
		//loginWin.removeEventListener(Events.ON_CLIENT_INFO, null);
		loginWin.addEventListener(Events.ON_CLIENT_INFO, new EventListener<Event>() {
	        @Override
	        public void onEvent(Event event) throws Exception {
	        	if (event.getData().toString().equals("login")) {
	        		doLogin();
	        	} else {
	        		disableLoginComponents();
	        		Executions.sendRedirect("/index.zul");
	        	}
	        	
	            
	        }
	    });
		Events.echoEvent("onClientInfo", loginWin, "login"); //echo an event back 
	}

	public TimescoperRecord getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(TimescoperRecord userAccount) {
		this.userAccount = userAccount;
	}

	public ListModelList<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(ListModelList<String> roleList) {
		this.roleList = roleList;
	}

	public String getPageCaption() {
		return pageCaption;
	}

	public void setPageCaption(String pageCaption) {
		this.pageCaption = pageCaption;
	}

	public ServerInfo getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(ServerInfo serverInfo) {
		log.debug(String.format("setServerInfo : %s %s %s", serverInfo.getHost(), serverInfo.getPort(), serverInfo.getDbName()));
		this.serverInfo = serverInfo;
	}

}
