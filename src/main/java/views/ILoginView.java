package views;

public interface ILoginView {
    void addTextLimiterToTextFields(int i);

    void hideSignUpError();

    void showSignUpError(String username);

    void hideLoginError();

    void showLoginError();

    void setController(ILoginController controller);
}
