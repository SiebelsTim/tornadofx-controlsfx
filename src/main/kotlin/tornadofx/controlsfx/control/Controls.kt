import impl.org.controlsfx.table.ColumnFilter
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import org.controlsfx.control.table.TableFilter
import java.util.function.BiPredicate

fun <T> TableView<T>.applyFilterControl(lazy: Boolean = false): TableFilter<T>? {

    val tableFilter = TableFilter.forTableView(this)
            .lazy(lazy)
            .apply()

    this.properties.put("TableFilter",tableFilter)

    return tableFilter
}

@Suppress("UNCHECKED_CAST")
val <T> TableView<T>.tableFilter: TableFilter<T> get() =  (properties["TableFilter"] as TableFilter<T>)

@Suppress("UNCHECKED_CAST")
val <T,C> TableColumn<T,C>.columnFilter: ColumnFilter<T, C> get() =
(tableView.properties["TableFilter"] as TableFilter<T>).getColumnFilter(this)
        .orElseThrow { Exception("TableFilter property not found!") } as ColumnFilter<T, C>

fun <T,C> TableColumn<T, C>.selectFilterValue(value: Any?) {
    tableView.tableFilter.selectValue(this,value)
}

fun <T,C> TableColumn<T, C>.selectAllFilterValues() {
    tableView.tableFilter.selectAllValues(this)
}

fun <T,C> TableColumn<T, C>.unselectFilterValue(value: Any?) {
    tableView.tableFilter.unselectValue(this,value)
}

fun <T,C> TableColumn<T, C>.unSelectAllFilterValues() {
    tableView.tableFilter.unSelectAllValues(this)
}

fun <T,C> TableColumn<T, C>.setFilterSearchStrategy(strategy: (String?,String?) -> Boolean) {
    columnFilter.searchStrategy = BiPredicate<String, String> { t, u -> strategy.invoke(t,u) }
}
