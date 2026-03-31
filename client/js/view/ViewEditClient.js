class ViewEditClient extends JView{
    constructor(user){
        super(user).mount_()
        this.defineLanguage()
    }

    _init(){
        A.form('formEditUser').a(
            A.p('txtEditUser').t(LANG.edit_client.toUpperCase()),
            A.div('groupName').a(
                A.label('lblName').t(LANG.name),
                A.inputText('inputUpdateName').v(this.name)
            ),
            A.div('groupEmail').a(
                A.label('lblEmail').t(LANG.email),
                A.inputText('inputUpdateEmail').v(this.email)
            ),
            A.div('groupNames').a(
                A.label('lblUsername').t(LANG.username+":").a(
                    A.inputText('inputUpdateUsername').v(this.username)
                ),
                A.label('lblNickname').t(LANG.nickname+":").a(
                    A.inputText('inputUpdateNickname').v(this.nickname)
                )
            ),
            A.label('lblLang').t(LANG.lang+":").a(
                A.inputSelect('inputUpdateLang').a(
                    A.inputOption('optEnglish').otv("English - US", "EN_US"),
                    A.inputOption('optPortugues').otv("Português - BR", "PT_BR")
                )
            ),
            A.div('groupButtons').a(
                A.inputButton('btnBack').v(LANG.back.toUpperCase()),
                A.inputSubmit('btnEditUser').v(LANG.edit_user.toUpperCase())
            )
        )
    }

    _limits(){
        this.$inputUpdateName.maxLength = 140
        this.$inputUpdateEmail.maxLength = 255
        this.$inputUpdateUsername.maxLength = 100
        this.$inputUpdateNickname.maxLength = 100
    }

    defineLanguage(){
        this.$inputUpdateLang.value = this.lang;
    }

}