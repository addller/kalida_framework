class ControllerIndex extends JMount{
    constructor() {
        super().mount_()
    }

    _init() {
        this.actionSet = new ActionSet('index')
        this.viewInitialize = this.actionSet.viewInitialize
        this.viewPresentation = new ViewPresentation()
        this.viewInitialize.setViewContent(this.viewPresentation)
    }
}
