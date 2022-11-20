
class ActionsNavigation extends JMount{
    constructor(location) {
        super()
        this.nameLocation = location.split('.')[0].toLocaleLowerCase()
        this.location = location
        this.mount_()
    }

    _init() {
        this.viewNavigation = new ViewNavigation();
    }

    _events() {
        this.ajustarExibicoes();
        let v = this.viewNavigation;
        v.$txtHome.onclick = _ => redirect('index')
        v.$txtSummary.onclick = _ => redirect('summary')
    }

    ajustarExibicoes() {
        let v = this.viewNavigation;
        switch (this.nameLocation) {
            case 'index':
                hide(v.$txtHome);
                break;
            case "summary":
                hide(v.$txtSummary)
                break
        }
    }

    displayItems(hasLoggin){
        hasLoggin ? show(this.viewNavigation) : hide(this.viewNavigation)
    }
    
}