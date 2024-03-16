import SwiftUI

@main
struct iOSApp: App {
    
    init() {
        UIView.setAnimationsEnabled(false)
    }
    
	var body: some Scene {
		WindowGroup {
            ZStack {
			    ContentView()
			}.preferredColorScheme(.dark).ignoresSafeArea(.keyboard)
		}
	}
}
