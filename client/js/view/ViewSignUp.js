class ViewSignUp extends JView{
    constructor(){
        super().mount_()
    }

    _init(){
        A.form('formSignUp').a(
            A.p('txtRegister').t(LANG.sign_up.toUpperCase()),
            A.div('groupName').a(
                A.label('lblName').t(LANG.name),
                A.inputText('inputName')
            ),
            A.div('groupEmail').a(
                A.label('lblEmail').t(LANG.email),
                A.inputText('inputEmail')
            ),
            A.div('groupNames').a(
                A.label('lblUsername').t(LANG.user_name).a(
                    A.inputText('inputUsername')
                ),
                A.label('lblNickName').t(LANG.nickName).a(
                    A.inputText('inputNickName')
                )
            ),
            A.div('groupPasswordAndLang').a(
                A.label('lblPassword').t(LANG.password).a(
                    A.inputPassword('inputPassword')
                ),
                A.label('lblLang').t(LANG.lang).a(
                    A.inputSelect('inputLang').a(
                        A.inputOption('optEnglish').otv('English - US', 'EN_US'),
                        A.inputOption('optPortuguese').otv('Português - BR', 'PT_BR')
                    )
                )
            ),
            A.div('groupButtons').a(
                A.inputButton('btnCancel').v(LANG.cancel.toUpperCase()),
                A.inputSubmit('btnSignUp').v(LANG.sign_up.toUpperCase())
            )
        )
        
    }

    _limits(){
        this.$inputName.maxLength = 140
        this.$inputEmail.maxLength = 255
        this.$inputUsername.maxLength = 100
        this.$inputNickName.maxLength = 100
        this.$inputPassword.maxLength = 200
    }

}