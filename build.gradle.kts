import com.jfrog.bintray.gradle.BintrayExtension

plugins {
	`java-library`
	`maven-publish`
	id("com.jfrog.bintray") version "1.8.4"
}

repositories {
	jcenter()
}

dependencies {
	compileOnly("org.jetbrains:annotations:19.0.0")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.1")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.1")
	testCompileOnly("org.jetbrains:annotations:19.0.0")
}

java {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
	withSourcesJar()
}

tasks {
	test {
		useJUnitPlatform()
	}
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			from(components["java"])
		}
	}
}

configure<BintrayExtension> {
	user = properties["bintray-user"] as String?
	key = properties["bintray-api-key"] as String?
	publish = true
	setPublications("maven")
	pkg(closureOf<BintrayExtension.PackageConfig> {
		repo = properties["mcparkour-bintray-repository"] as String?
		userOrg = properties["mcparkour-bintray-organization"] as String?
		name = project.name
		desc = project.description
		websiteUrl = "https://github.com/mcparkournet/octenace"
		issueTrackerUrl = "$websiteUrl/issues"
		vcsUrl = "$websiteUrl.git"
		setLicenses("MIT")
		setLabels("java", "mapper", "object-document-mapper", "odm")
	})
}