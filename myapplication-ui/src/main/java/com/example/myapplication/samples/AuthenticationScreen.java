package com.example.myapplication.samples;

import com.example.myapplication.MyUI;
import com.example.myapplication.samples.authentication.AccessControl;
import com.example.myapplication.samples.authentication.BasicAccessControl;
import com.example.myapplication.samples.authentication.BasicRegisterControl;
import com.example.myapplication.samples.authentication.LoginScreen;
import com.example.myapplication.samples.authentication.LoginScreen.LoginListener;
import com.example.myapplication.samples.authentication.RegisterScreen;
import com.example.myapplication.samples.authentication.RegisterScreen.RegisterListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

/**
 * Content of the UI when the user is logged in.
 * 
 * 
 */
public class AuthenticationScreen extends HorizontalLayout {
	
	Navigator navigator;
    
    private AccessControl accessControl = new BasicAccessControl();

    public AuthenticationScreen(MyUI ui) {

        CssLayout viewContainer = new CssLayout();
        viewContainer.addStyleName("valo-content");
        viewContainer.setSizeFull();

        navigator = new Navigator(ui, viewContainer);
        navigator.setErrorView(ErrorView.class);
        
        LoginScreen loginScreen = new LoginScreen(accessControl, new LoginListener() {
            @Override
            public void loginSuccessful() {
                ui.showMainView();
            }

			@Override
			public void enterRegister() {
				navigator.navigateTo(RegisterScreen.VIEW_NAME);
			}
        });
        
        RegisterScreen registerScreen = new RegisterScreen(new BasicRegisterControl(), new RegisterListener() {
        	
            @Override
            public void registerSuccessful() {
            	ui.showMainView();
            }
			@Override
			public void registerCancel() {
				navigator.navigateTo(LoginScreen.VIEW_NAME);
			}
        });
        
        navigator.addView(LoginScreen.VIEW_NAME, loginScreen);
        
        navigator.addView(RegisterScreen.VIEW_NAME, registerScreen);

        navigator.addViewChangeListener(viewChangeListener);

        addComponent(viewContainer);
        setExpandRatio(viewContainer, 1);
        setSizeFull();
        
        navigator.navigateTo(LoginScreen.VIEW_NAME);
    }

    // notify the view menu about view changes so that it can display which view
    // is currently active
    ViewChangeListener viewChangeListener = new ViewChangeListener() {

        @Override
        public boolean beforeViewChange(ViewChangeEvent event) {
            return true;
        }

        @Override
        public void afterViewChange(ViewChangeEvent event) {
            //navigator.setActiveView(event.getViewName());
        }

    };
}
