    class ViewLogin extends JView{
    constructor() {
        super().mount_()
    }

    _init() {
        A.form('formLogin').a([
            A.label('lblUsername').t(LANG.user_name),
            A.inputText('inputUsername').r(),
            A.label('lblPassword').t(LANG.password),
            A.inputPassword('inputPassword').r(),
            A.div('groupButtons').a(
                A.inputButton('btnSignUp').v(LANG.sign_up),
                A.inputSubmit('btnSignIn').v(LANG.sign_in)
            ),
            A.label('lblRecoverAccess').t(LANG.recover_access)
        ])

        this.$inputUsername.value = 'andrew_santos'
        this.$inputPassword.value = '#0xCUSTOM'
    }

    _limits(){
        this.$inputUsername.maxLength = 255
        this.$inputPassword.maxLength = 255
    }
}
