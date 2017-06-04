package com.example.myapplication.samples.authentication;

import java.io.Serializable;

import com.example.myapplication.MyUI;
import com.example.myapplication.samples.MainScreen;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * UI content when the user is not logged in yet.
 */
public class RegisterScreen extends CssLayout implements View {
	
	public static final String VIEW_NAME = "Register";

    private TextField username;
    private PasswordField password;
    private PasswordField confirmPassword;
    private Button cancel;
    private Button register;
    private RegisterListener registerListener;
    private RegisterControl registerControl;

    public RegisterScreen(RegisterControl registerControl, RegisterListener registerListener) {
        this.registerListener = registerListener;
        this.registerControl = registerControl;
        buildUI();
        username.focus();
    }

    private void buildUI() {
        addStyleName("register-screen");

        // login form, centered in the available part of the screen
        Component registerForm = buildRegisterForm();

        // layout to center register form when there is sufficient screen space
        // - see the theme for how this is made responsive for various screen
        // sizes
        VerticalLayout centeringLayout = new VerticalLayout();
        centeringLayout.setMargin(false);
        centeringLayout.setSpacing(false);
        centeringLayout.setStyleName("centering-layout");
        centeringLayout.addComponent(registerForm);
        centeringLayout.setComponentAlignment(registerForm,
                Alignment.MIDDLE_CENTER);

        // information text about logging in
        CssLayout registerInformation = buildRegisterInformation();

        addComponent(centeringLayout);
        addComponent(registerInformation);
    }

    private Component buildRegisterForm() {
        FormLayout registerForm = new FormLayout();

        registerForm.addStyleName("register-form");
        registerForm.setSizeUndefined();
        registerForm.setMargin(false);

        registerForm.addComponent(username = new TextField("Username", ""));
        username.setWidth(15, Unit.EM);
        registerForm.addComponent(password = new PasswordField("Password"));
        password.setWidth(15, Unit.EM);
        password.setDescription("Type your password");
        
        registerForm.addComponent(confirmPassword = new PasswordField("Confirm your password"));
        confirmPassword.setWidth(15, Unit.EM);
        confirmPassword.setDescription("Type your password again");
        
        CssLayout buttons = new CssLayout();
        buttons.setStyleName("buttons");
        registerForm.addComponent(buttons);

        buttons.addComponent(register = new Button("Register"));
        register.setDisableOnClick(true);
        register.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    register();
                } finally {
                	register.setEnabled(true);
                }
            }
        });
        register.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        register.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        
        buttons.addComponent(cancel = new Button("Cancel"));
        cancel.setDisableOnClick(true);
        cancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    cancel();
                } finally {
                	cancel.setEnabled(true);
                }
            }
        });
        cancel.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        cancel.addStyleName(ValoTheme.BUTTON_FRIENDLY);

        return registerForm;
    }

    private CssLayout buildRegisterInformation() {
        CssLayout loginInformation = new CssLayout();
        loginInformation.setStyleName("register-information");
        Label loginInfoText = new Label(
                "<h1>Register Information</h1>"
                        + "Register as &quot;admin&quot; to have full access. Register with any other username to have read-only access. For all users, any password is fine",
                ContentMode.HTML);
        loginInfoText.setSizeFull();
        loginInformation.addComponent(loginInfoText);
        return loginInformation;
    }

    private void register() {
        if (registerControl.register(username.getValue(), password.getValue(), confirmPassword.getValue())) {
            registerListener.registerSuccessful();
        } else {
            showNotification(new Notification("Register failed",
                    "Your username is already in use. Change it and try again.",
                    Notification.Type.HUMANIZED_MESSAGE));
            username.focus();
        }
    }
    
    private void cancel(){
    	registerListener.registerCancel();
    }

    private void showNotification(Notification notification) {
        // keep the notification visible a little while after moving the
        // mouse, or until clicked
        notification.setDelayMsec(2000);
        notification.show(Page.getCurrent());
    }

    public interface RegisterListener extends Serializable {
        void registerSuccessful();
        void registerCancel();
    }

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
