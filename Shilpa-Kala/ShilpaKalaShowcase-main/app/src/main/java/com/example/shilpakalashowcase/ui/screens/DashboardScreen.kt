package com.example.shilpakalashowcase.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shilpakalashowcase.MainViewModel
import com.example.shilpakalashowcase.data.Artwork
import com.example.shilpakalashowcase.data.UserRole

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, viewModel: MainViewModel) {
    val user by viewModel.currentUser.collectAsState()
    
    // Using remember to avoid re-creating mock data on every recomposition (Performance Fix)
    val artworks = remember {
        listOf(
            Artwork("1", "A1", "Arshid", "Stone Buddha", "Hand carved", "https://picsum.photos/seed/1/400/600", "Spiritual", "Hoysala", 1200.0),
            Artwork("2", "A1", "Arshid", "Wooden Elephant", "Hoysala Style", "https://picsum.photos/seed/2/400/300", "Animal", "Chola", 450.0),
            Artwork("3", "A1", "Arshid", "Marble Goddess", "Fine detail", "https://picsum.photos/seed/3/400/500", "Deity", "Gandhara", 2500.0),
            Artwork("4", "A1", "Arshid", "Ancient Pillar", "Traditional", "https://picsum.photos/seed/4/400/700", "Architecture", "Pallava", 800.0),
            Artwork("5", "A1", "Arshid", "Bronze Dancer", "Metal casting", "https://picsum.photos/seed/5/400/400", "Human", "Chola", 1500.0)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("SHILPA-KALA", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                        Text("Traditional Excellence", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.Default.AccountCircle, "Profile", tint = MaterialTheme.colorScheme.primary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                tonalElevation = 0.dp
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.GridView, null) },
                    label = { Text("Gallery") },
                    selected = true,
                    onClick = { }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.History, null) },
                    label = { Text("History") },
                    selected = false,
                    onClick = { navController.navigate("history") }
                )
                if (user?.role == UserRole.ARTIST) {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.AddCircle, null) },
                        label = { Text("Upload") },
                        selected = false,
                        onClick = { }
                    )
                }
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, null) },
                    label = { Text("Settings") },
                    selected = false,
                    onClick = { navController.navigate("settings") }
                )
            }
        }
    ) { padding ->
        // Gradient background for modern feel
        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
                    )
                )
            )
            .padding(padding)
        ) {
            Column {
                // Horizontal category selector for modern UX
                CategoryList()

                Text(
                    "Heritage Stories",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
                    fontWeight = FontWeight.Bold
                )
                
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val stories = listOf(
                        "Hoysala Art" to Color(0xFFE91E63),
                        "Chola Bronze" to Color(0xFFFF9800),
                        "Gandhara" to Color(0xFF4CAF50)
                    )
                    items(stories.size) { index ->
                        HeritageCard(stories[index].first, stories[index].second)
                    }
                }

                Text(
                    "Featured Sculptures",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold
                )

                // Staggered grid for high-end look
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalItemSpacing = 16.dp
                ) {
                    // Use keys for better list performance
                    items(artworks, key = { it.id }) { artwork ->
                        ArtworkCard(artwork) {
                            // Detail logic
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryList() {
    val categories = listOf("All", "Stone", "Wood", "Metal", "Marble")
    var selected by remember { mutableStateOf("All") }
    
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories.size) { index ->
            val cat = categories[index]
            val isSelected = selected == cat
            FilterChip(
                selected = isSelected,
                onClick = { selected = cat },
                label = { Text(cat) },
                shape = CircleShape,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}

@Composable
fun HeritageCard(title: String, color: Color) {
    Card(
        modifier = Modifier.size(160.dp, 90.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.15f)),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.3f))
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(title, style = MaterialTheme.typography.titleSmall, color = color, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ArtworkCard(artwork: Artwork, onClick: () -> Unit) {
    val context = LocalContext.current
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
    ) {
        Column {
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(artwork.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = artwork.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 150.dp, max = 250.dp)
                        .clip(RoundedCornerShape(24.dp)),
                    contentScale = ContentScale.Crop
                )
                
                // Modern Badge for Category
                Surface(
                    modifier = Modifier.padding(12.dp).align(Alignment.TopStart),
                    shape = CircleShape,
                    color = Color.Black.copy(alpha = 0.5f)
                ) {
                    Text(
                        artwork.category,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(artwork.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(artwork.artistName, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("$${artwork.price.toInt()}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.weight(1f))
                    
                    IconButton(
                        onClick = {
                            val message = "Professional Inquiry: '${artwork.title}' by ${artwork.artistName}"
                            val uri = Uri.parse("https://api.whatsapp.com/send?phone=9682397579&text=${Uri.encode(message)}")
                            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                        },
                        modifier = Modifier.size(32.dp).background(MaterialTheme.colorScheme.primary, CircleShape)
                    ) {
                        Icon(Icons.Default.ArrowForward, null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
    }
}
