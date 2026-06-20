class ViewMenuItemUser extends JView{
    constructor() {
        super().mount_()
        this.setImgPerfil(API_KALIDA.getImagePerfil())
        this.numNotifications = 0
    }

    _init() {
        A.div('view').a(
            A.div('groupMenu').a(
                A.div('groupNotification').a(
                    A.span('iconNotificacao'),
                    A.span('spanNumNotifications')
                ),
                A.img('imgUser'),
            ),
            A.div('divMenu').a(
                E.hr(),
                A.span('spanEditarPerfil').t(LANG.edit_profile),
                E.hr(),
                A.span('spanSignOut').t(LANG.sign_out)
            ),
            A.div('divNotifications')
        )
        this.$imgUser.title = API_KALIDA.getUser()?.nick_name_;
    }

    setImgPerfil(imgPerfil) {
        setAttribute(this.$imgUser, ['src', imgPerfil || 'img/user_default.png']);
    }

    addViewRowNotification(viewRowNotification){
        this.numNotifications++
        if(this.reverseInsertionNotification && this.$divNotifications.firstChild){
            insertBefore(this.$divNotifications.firstChild, viewRowNotification)
            return
        }
        this.$divNotifications.a(viewRowNotification)
    }

    changeNumNotifications(){
        let num = this.numNotifications
        if(num > 99) num = num +'+'
        this.$spanNumNotifications.t(num || '')
        num && this.removeClasses_(this.$spanNumNotifications, 'has_no_notification') || this.insertClasses_(this.$spanNumNotifications, 'has_no_notification')
        
    }
}


