class ViewEditProfile extends JView{
    constructor(user){
        super(user).mount_()
        this.defineLanguage()
    }

    _init(){
        A.form('formEditProfile').a(
            A.p('txtEditProfile').t(LANG.edit_profile.toUpperCase()),
            A.div('groupName').a(
                A.label('lblName').t(LANG.name),
                A.inputText('inputUpdateName').v(this.name)
            ),
            A.div('groupEmail').a(
                A.label('lblEmail').t(LANG.email),
                A.inputText('inputUpdateEmail').v(this.email)
            ),
            A.div('groupNames').a(
                A.label('lblUsername').t(LANG.user_name+":").a(
                    A.inputText('inputUpdateUsername').v(this.username)
                ),
                A.label('lblNickName').t(LANG.nickName+":").a(
                    A.inputText('inputUpdateNickName').v(this.nickName)
                )
            ),
            A.label('lblLang').t(LANG.lang+":").a(
                A.inputSelect('inputUpdateLang').a(
                    A.inputOption('optEnglish').otv("English - US", "EN_US"),
                    A.inputOption('optPortugues').otv("Português - BR", "PT_BR")
                )
            ),
            A.inputSubmit('btnEditProfile').v(LANG.edit_profile.toUpperCase())
        )
    }

    _limits(){
        this.$inputUpdateName.maxLength = 140
        this.$inputUpdateEmail.maxLength = 255
        this.$inputUpdateUsername.maxLength = 100
        this.$inputUpdateNickName.maxLength = 100
    }

    defineLanguage(){
        this.$inputUpdateLang.value = this.lang;
    }

}