package es.jnsoft.whatweath.presentation.ui.main

import androidx.annotation.StringRes
import es.jnsoft.domain.model.Units
import es.jnsoft.whatweath.R

sealed class MenuAction(@StringRes val label: Int, val units: Units) {
    object Standard : MenuAction(R.string.menu_standard, Units.STANDARD)
    object Metric : MenuAction(R.string.menu_metric, Units.METRIC)
    object Imperial : MenuAction(R.string.menu_imperial, Units.IMPERIAL)
}