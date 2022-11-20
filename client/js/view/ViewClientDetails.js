class ViewClientDetails extends JView{
    constructor(user){
        super(user).mount_()
        this.loadDefaultImgPerfil()
    }

    _init(){
        A.div('view').a(
            A.p('txtUser').t(LANG.user.toUpperCase()),
            A.img('imgPerfil'),
            A.div('groupName').a(
                A.span('txtName').t(LANG.name+":"),
                A.span('spanName').t(this.name)
            ),
            A.div('groupUsername').a(
                A.span('txtUsername').t(LANG.username+":"),
                A.span('spanUsername').t(this.username)
            ),
            A.div('groupNickName').a(
                A.span('txtNickName').t(LANG.nickName+":"),
                A.span('spanNickName').t(this.nickName)
            ),
            A.div('groupLang').a(
                A.span('txtLang').t(LANG.lang+":"),
                A.span('spanLang').t(this._normalizeLang())
            ),
            A.div('groupEmail').a(
                A.span('txtEmail').t(LANG.email+":"),
                A.span('spanEmail').t(this.email)
            ),
            A.p('txtExperiences').t(LANG.experiences.toUpperCase()),
            A.div('divExperiences')
        )   
    }

    _normalizeLang(){
        return {
            "PT_BR":"Português - BR",
            "EN_US":"English - US"
        }[this.lang]
    }

    addViewRowExperience(viewRowExperience){
        this.$divExperiences.a(viewRowExperience)
    }

    loadDefaultImgPerfil(){
        this.$imgPerfil.src = 'img/user_default.png'
    }

}