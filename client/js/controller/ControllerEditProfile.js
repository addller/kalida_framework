class ControllerEditProfile extends JMount{
    constructor() {
        super().mount_()
    }

    _init() {
        this.actionSet = new ActionSet('edit_user')
        this.viewInitialize = this.actionSet.viewInitialize
    }

    _requests(){
        let userId = API_KALIDA.getUser().userId_;
        JRequest.prepare(API_KALIDA.toUsers(userId))
            .inResponse(
                user => {
                    let viewContentEditProfile = new ViewContentEditProfile(user)
                    this.viewInitialize.setViewContent(viewContentEditProfile)
                    this.configureViewContentEditProfile(viewContentEditProfile)
                },
                error => new Aspect(error)
            ).get()
    }

    configureViewContentEditProfile(viewContentEditProfile){
        this.configureViewChangeImgPerfil(viewContentEditProfile.$viewChangeImgPerfil)
        this.configureViewEditProfile(viewContentEditProfile.$viewEditProfile)
        this.configureViewChangePassword(viewContentEditProfile.$viewChangePassword)
    }

    configureViewEditProfile(viewEditProfile){
        this.requestEditProfile(viewEditProfile)
    }

    requestEditProfile(viewEditProfile){
        viewEditProfile.$formEditProfile.onsubmit = e => {
            consume(e)
            let source = viewEditProfile.viewValues_(true),
                enderessable = API_KALIDA.toUsers();
            
            if(viewEditProfile.isUpdated_()){
                toast(LANG.nothing_changed)
                return
            }
            JRequest.prepare(enderessable, source)
                .inResponse(
                    (_, response) => {
                        viewEditProfile.updateView_(source)

                        let title = LANG.information,
                            message = LANG.user_saved;
                        debugger
                        new Dialog(message, title, () => {
                            ActionsLogin.saveLogin(response, null, false)
                        }).hideBtnNegate(LANG.close).show();
                    },
                    error => new Aspect(error),
                ).put()
        }
    }

    configureViewChangePassword(viewChangePassword){
        this.requestChangePassword(viewChangePassword)
    }

    requestChangePassword(viewChangePassword){
        viewChangePassword.$formChangePassword.onsubmit = e => {
            consume(e)

            let source = viewChangePassword.viewValues_(true),
                enderessable = API_KALIDA.toAuth('password');
            let waiter = new Waiter(LANG.processing_request).hideDivAnimation();

            if(viewChangePassword.isUpdated_()) return
            JRequest.prepare(enderessable, source)
                .inSequence(
                    _ => waiter.wait(),
                    _ => waiter.free(),
                    _ => {
                        let title = LANG.information,
                            message = LANG.password_updated;
                        new Dialog(message, title)
                            .hideBtnNegate(LANG.close)
                            .show();
                    },
                    error => {
                        switch(error.message){
                            case 'Bad credentials': toast(LANG.invalid_current_password)
                                break;
                            default:
                                new Aspect(error)
                        }
                    },
                ).put()
        }
    }

    configureViewChangeImgPerfil(viewChangeImgPerfil){
        imgPreview(viewChangeImgPerfil.$inputUpdateImgPerfil, viewChangeImgPerfil.$imgPreview);
        this.requestChangeImgPerfil(viewChangeImgPerfil)
    }

    requestChangeImgPerfil(viewChangeImgPerfil){
        viewChangeImgPerfil.$formChangeImgPerfil.onsubmit = e => {
            consume(e)
            let {$inputUpdateImgPerfil} = viewChangeImgPerfil,
                {files} = $inputUpdateImgPerfil;

            if(files.length){
                let formData = new FormData();
                
                formData.append('imgPerfil', files[0]);
            
                JRequest.prepareFiles(API_KALIDA.toImgPerfil(), formData)
                    .inResponse(
                        imgPerfil => this.actionSet.actionsMenu.setImgPerfil(imgPerfil),
                        error => new Aspect(error)
                    ).put()
            }
            
        }
    }

}
