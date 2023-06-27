package com.ss.gpacalculator.screen.semester

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ss.gpacalculator.R
import com.ss.gpacalculator.model.SubjectModel
import com.ss.gpacalculator.ui.common.DefaultButton
import com.ss.gpacalculator.ui.common.DefaultCenterTopAppBar
import com.ss.gpacalculator.ui.common.DefaultIcon
import com.ss.gpacalculator.ui.common.DefaultIconButton
import com.ss.gpacalculator.ui.common.DefaultScaffold
import com.ss.gpacalculator.ui.common.DefaultText
import com.ss.gpacalculator.ui.theme.GpaCalculatorTheme
import com.ss.gpacalculator.utils.State

@Composable
fun SemesterScreen(
    subjectsState: State<List<SubjectModel>>,
    onAddClick: () -> Unit,
    onCalculateClick: () -> String,
    onBackClick: () -> Unit
) {
    var resultDialog by remember { mutableStateOf("") }

    DefaultScaffold(
        topBar = { SemesterTopAppBar(onAddClick = onAddClick, onBackClick = onBackClick) }
    ) {
        when (subjectsState) {
            is State.Success -> {
                subjectsState.data?.let { SubjectsList(it, modifier = Modifier.weight(1f)) }

                DefaultButton(
                    text = R.string.semester_calculate,
                    onClick = { resultDialog = onCalculateClick() },
                    modifier = Modifier.padding(16.dp)
                )
            }

            else -> Unit
        }
    }

    if (resultDialog.isNotBlank()) ResultDialog(
        gpaResult = resultDialog,
        onDismiss = { resultDialog = "" }
    )
}

@Composable
private fun SemesterTopAppBar(
    onAddClick: () -> Unit,
    onBackClick: () -> Unit
) = DefaultCenterTopAppBar(
    title = R.string.semester_screen,
    actions = { DefaultIconButton(icon = Icons.Default.Add, onClick = onAddClick) },
    navigationIcon = { DefaultIconButton(onClick = onBackClick, icon = Icons.Default.ArrowBack) },
)

@Composable
private fun SubjectsList(
    subjects: List<SubjectModel>,
    modifier: Modifier
) = LazyColumn(modifier = modifier) {
    items(items = subjects, key = { it.number }) {
        SubjectItem(subject = it)
    }
}

@Composable
private fun SubjectItem(subject: SubjectModel) = ListItem(
    headlineContent = { CreditTextField(subject = subject) },
    trailingContent = { GradeDropDown(subject = subject) },
    leadingContent = { Text(text = subject.number.toString()) }
)

@Composable
private fun CreditTextField(subject: SubjectModel) {
    var credit by remember { mutableStateOf("") }
    subject.credit = if (credit.isNotBlank()) credit.last() else ' '

    OutlinedTextField(
        value = credit,
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        ),
        label = { DefaultText(text = R.string.credit) },
        onValueChange = { credit = it.lastOrNull()?.toString() ?: "" }
    )
}

@Composable
private fun GradeDropDown(subject: SubjectModel) {
    val grades = stringArrayResource(id = R.array.grades)
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(grades[0]) }
    subject.grade = selected

    OutlinedTextField(
        readOnly = true,
        value = subject.grade,
        onValueChange = {},
        modifier = Modifier.width(115.dp),
        trailingIcon = {
            DefaultIconButton(onClick = { expanded = true }, icon = Icons.Default.ArrowDropDown)
        },
        label = { DefaultText(text = R.string.grade) }
    )

    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        grades.forEach { grade ->
            DropdownMenuItem(
                text = { Text(text = grade) },
                onClick = { selected = grade; expanded = false }
            )
        }
    }
}

@Composable
private fun ResultDialog(gpaResult: String, onDismiss: () -> Unit) = AlertDialog(
    onDismissRequest = onDismiss,
    icon = { DefaultIcon(imageVector = Icons.Default.Calculate, modifier = Modifier.size(50.dp)) },
    title = { Text(text = gpaResult, textAlign = TextAlign.Center) },
    confirmButton = {}
)

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "AR")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "EN")
private fun SemesterScreenPreview() = GpaCalculatorTheme {
    SemesterScreen(
        subjectsState = State.Success(listOf()),
        onAddClick = {},
        onCalculateClick = { "" },
        onBackClick = {}
    )
}