class ViewNavigation extends JView{
    constructor() {
        super().mount_()
    }

    _init() {
        A.div("view").a([
            A.p("txtHome").cls('item').a(
                A.span("iconHome"),
                A.span('spanHome').t(LANG.home),
            ),
            A.p("txtSummary").cls('item').a(
                A.span("iconSummary"),
                A.span('spanSummary').t(LANG.summary),
            )
        ])
    }
}
