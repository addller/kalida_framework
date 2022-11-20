class ControllerSignUp extends JMount{
    constructor() {
        super().mount_()
    }

    _init() {
        this.actionSet = new ActionSet('sign_up')
        this.viewInitialize = this.actionSet.viewInitialize
        this.viewSignUp = new ViewSignUp()
        this.viewInitialize.setViewContent(this.viewSignUp)
        this.viewInitialize.disapendDivNavigation()
    }

    _events(){
        this.eventSignUp()
        this.viewSignUp.$btnCancel.onclick = _ => redirect('index')
    }

    eventSignUp(){
        let {viewSignUp} = this,
            {$formSignUp} = viewSignUp;
        
        $formSignUp.onsubmit = e => {
            consume(e)
            let source = viewSignUp.viewValues_(),
                enderessable = API_KALIDA.toUsers();

            let waiter = new Waiter(LANG.waiting).hideDivAnimation()
            
            JRequest.prepare(enderessable, source)
                .inSequence(
                    _ => {
                        waiter.wait()
                        viewSignUp.$btnSignUp.d(true)
                    },
                    _ => {
                        waiter.free()
                        viewSignUp.$btnSignUp.d(false)
                    },
                    _ => {
                        $formSignUp.reset()

                        let title = LANG.information,
                            message = LANG.registration_performed;
                        new Dialog(message, title, () => {
                            redirect('index')
                        }).hideBtnNegate(LANG.close).show();
                    },
                    error => {
                        console.log(error)
                        new Aspect(error)
                    }
                ).post()
        }
    }
}
